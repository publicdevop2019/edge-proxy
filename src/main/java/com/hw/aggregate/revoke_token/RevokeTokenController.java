package com.hw.aggregate.revoke_token;

import com.hw.aggregate.revoke_token.command.CreateRevokeTokenCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.hw.shared.AppConstant.HTTP_HEADER_CHANGE_ID;

/**
 * zuul own endpoints are getting internal forward,
 * internal forwarding is having issue with form data,
 */
@RestController
@RequestMapping(produces = "application/json", path = "revoke-tokens")
public class RevokeTokenController {

    @Autowired
    private RootRevokeTokenApplicationService rootRevokeTokenApplicationService;
    @Autowired
    private AppRevokeTokenApplicationService appRevokeTokenApplicationService;
    @Autowired
    private AdminRevokeTokenApplicationService adminRevokeTokenApplicationService;

    @PostMapping("root")
    public ResponseEntity<?> createForRoot(@RequestBody CreateRevokeTokenCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        rootRevokeTokenApplicationService.create(command, changeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("change/root/{id}")
    public ResponseEntity<?> rollbackChangeForRoot(@PathVariable String id) {
        rootRevokeTokenApplicationService.rollbackCreateOrDelete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("app")
    public ResponseEntity<?> createForApp(@RequestBody CreateRevokeTokenCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        appRevokeTokenApplicationService.create(command, changeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("change/app/{id}")
    public ResponseEntity<?> rollbackChangeForApp(@PathVariable String id) {
        appRevokeTokenApplicationService.rollbackCreateOrDelete(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("admin")
    public ResponseEntity<?> createForAdmin(@RequestBody CreateRevokeTokenCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        adminRevokeTokenApplicationService.create(command, changeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("change/admin/{id}")
    public ResponseEntity<?> rollbackChangeForAdmin(@PathVariable String id) {
        adminRevokeTokenApplicationService.rollbackCreateOrDelete(id);
        return ResponseEntity.ok().build();
    }
}
