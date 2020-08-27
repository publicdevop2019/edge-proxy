package com.hw.aggregate.revoke_token;

import com.hw.aggregate.endpoint.command.CreateBizEndpointCommand;
import com.hw.aggregate.revoke_token.command.CreateRevokeTokenCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    public ResponseEntity<?> createForRoot(@RequestBody CreateRevokeTokenCommand command, HttpServletRequest request, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        rootRevokeTokenApplicationService.create(command, request, changeId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("app")
    public ResponseEntity<?> createForApp(@RequestBody CreateRevokeTokenCommand command, HttpServletRequest request, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        appRevokeTokenApplicationService.create(command, request, changeId);
        return ResponseEntity.ok().build();
    }
    @PostMapping("admin")
    public ResponseEntity<?> createForAdmin(@RequestBody CreateRevokeTokenCommand command, HttpServletRequest request, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        adminRevokeTokenApplicationService.create(command, request, changeId);
        return ResponseEntity.ok().build();
    }
}
