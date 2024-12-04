package red.tetracube.hubcentral.auth;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import red.tetracube.hubcentral.auth.payloads.LoginRequestPayload;
import red.tetracube.hubcentral.auth.payloads.LoginResponsePayload;
import red.tetracube.hubcentral.database.entities.HubEntity;
import red.tetracube.hubcentral.domain.Result;
import red.tetracube.hubcentral.domain.exceptions.HubCentralException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Set;

@ApplicationScoped
public class HubAuthServices {

    @ConfigProperty(name = "mp.jwt.verify.audiences")
    Set<String> jwtAudiences;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String jwtIssuer;

    private final static Logger LOG = LoggerFactory.getLogger(HubAuthServices.class);

    public Result<LoginResponsePayload> generateTokenForHub(LoginRequestPayload request) {
        var name = request.name().trim();
        Optional<HubEntity> optionalHub;
        try {
            optionalHub = HubEntity.<HubEntity>find("name", request.name()).firstResultOptional();
        } catch (Exception ex) {
            LOG.error("Error on repository query:", ex);
            return Result.failed(
                    new HubCentralException.RepositoryException(ex)
            );
        }

        if (optionalHub.isEmpty()) {
            LOG.warn("No hub named {} found", name);
            return Result.failed(
                    new HubCentralException.EntityNotFoundException("Cannot find any Hub with given name")
            );
        }

        var theHub = optionalHub.get();
        var accessCode = request.accessCode();
        if (!BcryptUtil.matches(accessCode, theHub.accessCode)) {
            LOG.warn("The access code does not match");
            return Result.failed(
                    new HubCentralException.UnauthorizedException("Access code is invalid")
            );
        }

        var tokenIssueTS = Instant.now();
        var tokenExpirationTS = Instant.now().plus(30, ChronoUnit.DAYS);
        var token = Jwt
                .claims()
                .issuer(jwtIssuer)
                .issuedAt(tokenIssueTS)
                .upn(theHub.name)
                .audience(jwtAudiences)
                .claim("hub_id", theHub.id)
                .expiresAt(tokenExpirationTS)
                .sign();
        LOG.info("Hub access granted, created the JWT");
        return Result.success(
                new LoginResponsePayload(
                        theHub.id,
                        theHub.name,
                        token
                )
        );
    }

}
