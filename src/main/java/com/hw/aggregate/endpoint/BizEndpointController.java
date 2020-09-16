package com.hw.aggregate.endpoint;

import com.github.fge.jsonpatch.JsonPatch;
import com.hw.aggregate.endpoint.command.CreateBizEndpointCommand;
import com.hw.aggregate.endpoint.command.UpdateBizEndpointCommand;
import com.hw.aggregate.endpoint.representation.RootBizEndpointCardRep;
import com.hw.aggregate.endpoint.representation.RootBizEndpointRep;
import com.hw.shared.sql.SumPagedRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static com.hw.shared.AppConstant.*;

@RestController
@RequestMapping(produces = "application/json", path = "endpoints")
public class BizEndpointController {

    @Autowired
    RootBizEndpointApplicationService securityProfileService;

    @PostMapping("root")
    public ResponseEntity<Void> createForRoot(@RequestBody CreateBizEndpointCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        return ResponseEntity.ok().header("Location", String.valueOf(securityProfileService.create(command, changeId).getId())).build();
    }

    @GetMapping("root")
    public ResponseEntity<SumPagedRep<RootBizEndpointCardRep>> readForRootByQuery(@RequestParam(value = HTTP_PARAM_QUERY, required = false) String queryParam,
                                                                                  @RequestParam(value = HTTP_PARAM_PAGE, required = false) String pageParam,
                                                                                  @RequestParam(value = HTTP_PARAM_SKIP_COUNT, required = false) String config) {
        return ResponseEntity.ok(securityProfileService.readByQuery(queryParam, pageParam, config));
    }

    @GetMapping("root/{id}")
    public ResponseEntity<RootBizEndpointRep> readForRootById(@PathVariable Long id,
                                                              HttpServletRequest request) {
        return ResponseEntity.ok(securityProfileService.readById(id));
    }

    @PutMapping("root/{id}")
    public ResponseEntity<Void> replaceForRootById(@RequestBody UpdateBizEndpointCommand command, @PathVariable Long id, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        securityProfileService.replaceById(id, command, changeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("root/{id}")
    public ResponseEntity<Void> deleteForRootById(@PathVariable Long id, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        securityProfileService.deleteById(id, changeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("root")
    public ResponseEntity<Void> deleteForAdminByQuery(@RequestParam(value = HTTP_PARAM_QUERY, required = false) String queryParam, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        securityProfileService.deleteByQuery(queryParam, changeId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "root/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Void> patchForRootById(@PathVariable(name = "id") Long id, @RequestBody JsonPatch patch, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(HTTP_HEADER_CHANGE_ID, changeId);
        securityProfileService.patchById(id, patch, params);
        return ResponseEntity.ok().build();
    }
}
