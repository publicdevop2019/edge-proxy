package com.hw.aggregate.endpoint;

import com.github.fge.jsonpatch.JsonPatch;
import com.hw.aggregate.endpoint.command.RootCreateBizEndpointCommand;
import com.hw.aggregate.endpoint.command.RootUpdateBizEndpointCommand;
import com.hw.aggregate.endpoint.representation.RootBizEndpointCardRep;
import com.hw.aggregate.endpoint.representation.RootBizEndpointRep;
import com.hw.config.InternalForwardHelper;
import com.hw.shared.sql.SumPagedRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static com.hw.shared.AppConstant.*;

@RestController
@RequestMapping(produces = "application/json", path = "proxy/endpoints")
public class BizEndpointController {

    @Autowired
    RootBizEndpointApplicationService securityProfileService;
    @Autowired
    InternalForwardHelper internalForwardHelper;

    @PostMapping("root")
    public ResponseEntity<Void> createForRoot(@RequestBody RootCreateBizEndpointCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        return ResponseEntity.ok().header("Location", String.valueOf(securityProfileService.create(command, changeId).getId())).build();
    }

    @GetMapping("root")
    public ResponseEntity<SumPagedRep<RootBizEndpointCardRep>> readForRootByQuery(@RequestParam(value = HTTP_PARAM_QUERY, required = false) String queryParam,
                                                                                  @RequestParam(value = HTTP_PARAM_PAGE, required = false) String pageParam,
                                                                                  @RequestParam(value = HTTP_PARAM_SKIP_COUNT, required = false) String config, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        return ResponseEntity.ok(securityProfileService.readByQuery(queryParam, pageParam, config));
    }

    @GetMapping("root/{id}")
    public ResponseEntity<RootBizEndpointRep> readForRootById(@PathVariable Long id,
                                                              HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        return ResponseEntity.ok(securityProfileService.readById(id));
    }

    @PutMapping("root/{id}")
    public ResponseEntity<Void> replaceForRootById(@RequestBody RootUpdateBizEndpointCommand command, @PathVariable Long id, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        securityProfileService.replaceById(id, command, changeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("root/{id}")
    public ResponseEntity<Void> deleteForRootById(@PathVariable Long id, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        securityProfileService.deleteById(id, changeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("root")
    public ResponseEntity<Void> deleteForAdminByQuery(@RequestParam(value = HTTP_PARAM_QUERY, required = false) String queryParam, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        securityProfileService.deleteByQuery(queryParam, changeId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "root/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Void> patchForRootById(@PathVariable(name = "id") Long id, @RequestBody JsonPatch patch, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        HashMap<String, Object> params = new HashMap<>();
        params.put(HTTP_HEADER_CHANGE_ID, changeId);
        securityProfileService.patchById(id, patch, params);
        return ResponseEntity.ok().build();
    }
}
