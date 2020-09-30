package com.hw.aggregate.revoke_token;

import com.hw.aggregate.revoke_token.command.CreateRevokeTokenCommand;
import com.hw.aggregate.revoke_token.representation.RootRevokeTokenCardRep;
import com.hw.config.InternalForwardHelper;
import com.hw.shared.sql.SumPagedRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.hw.shared.AppConstant.*;

/**
 * zuul own endpoints are getting internal forward,
 * internal forwarding is having issue with form data,
 */
@RestController
@RequestMapping(produces = "application/json", path = "proxy/revoke-tokens")
public class RevokeTokenController {

    @Autowired
    private RootRevokeTokenApplicationService rootRevokeTokenApplicationService;
    @Autowired
    private AppRevokeTokenApplicationService appRevokeTokenApplicationService;
    @Autowired
    private AdminRevokeTokenApplicationService adminRevokeTokenApplicationService;
    @Autowired
    InternalForwardHelper internalForwardHelper;
    @PostMapping("root")
    public ResponseEntity<Void> createForRoot(@RequestBody CreateRevokeTokenCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        rootRevokeTokenApplicationService.create(command, changeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("root")
    public ResponseEntity<SumPagedRep<RootRevokeTokenCardRep>> readForRootByQuery(@RequestParam(value = HTTP_PARAM_QUERY, required = false) String queryParam,
                                                                                  @RequestParam(value = HTTP_PARAM_PAGE, required = false) String pageParam,
                                                                                  @RequestParam(value = HTTP_PARAM_SKIP_COUNT, required = false) String config,
                                                                                  HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        return ResponseEntity.ok(rootRevokeTokenApplicationService.readByQuery(queryParam, pageParam, config));
    }


    @PostMapping("app")
    public ResponseEntity<Void> createForApp(@RequestBody CreateRevokeTokenCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        appRevokeTokenApplicationService.create(command, changeId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("admin")
    public ResponseEntity<Void> createForAdmin(@RequestBody CreateRevokeTokenCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        adminRevokeTokenApplicationService.create(command, changeId);
        return ResponseEntity.ok().build();
    }
}
