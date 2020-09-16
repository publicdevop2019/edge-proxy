package com.hw.aggregate.revoke_token;

import com.hw.aggregate.revoke_token.command.CreateRevokeTokenCommand;
import com.hw.aggregate.revoke_token.representation.RootRevokeTokenCardRep;
import com.hw.shared.sql.SumPagedRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.hw.shared.AppConstant.*;

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
    public ResponseEntity<Void> createForRoot(@RequestBody CreateRevokeTokenCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        rootRevokeTokenApplicationService.create(command, changeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("root")
    public ResponseEntity<SumPagedRep<RootRevokeTokenCardRep>> readForRootByQuery(@RequestParam(value = HTTP_PARAM_QUERY, required = false) String queryParam,
                                                                                  @RequestParam(value = HTTP_PARAM_PAGE, required = false) String pageParam,
                                                                                  @RequestParam(value = HTTP_PARAM_SKIP_COUNT, required = false) String config) {
        return ResponseEntity.ok(rootRevokeTokenApplicationService.readByQuery(queryParam, pageParam, config));
    }


    @PostMapping("app")
    public ResponseEntity<Void> createForApp(@RequestBody CreateRevokeTokenCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        appRevokeTokenApplicationService.create(command, changeId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("admin")
    public ResponseEntity<Void> createForAdmin(@RequestBody CreateRevokeTokenCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        adminRevokeTokenApplicationService.create(command, changeId);
        return ResponseEntity.ok().build();
    }
}
