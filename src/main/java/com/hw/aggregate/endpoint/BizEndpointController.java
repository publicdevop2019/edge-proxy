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

import static com.hw.shared.AppConstant.*;

@RestController
@RequestMapping(produces = "application/json", path = "endpoints")
public class BizEndpointController {

    @Autowired
    RootBizEndpointApplicationService securityProfileService;

    @PostMapping("root")
    public ResponseEntity createForRoot(@RequestBody CreateBizEndpointCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        return ResponseEntity.ok().header("Location", String.valueOf(securityProfileService.createForRoot(command, changeId, request).getId())).build();
    }

    @GetMapping("root")
    public SumPagedRep<RootBizEndpointCardRep> readForRootByQuery(@RequestParam(value = HTTP_PARAM_QUERY, required = false) String queryParam,
                                                                  @RequestParam(value = HTTP_PARAM_PAGE, required = false) String pageParam,
                                                                  @RequestParam(value = HTTP_PARAM_SKIP_COUNT, required = false) String config,
                                                                  HttpServletRequest request) {
        return securityProfileService.readAll(request, queryParam, pageParam, config);
    }

    @GetMapping("root/{id}")
    public RootBizEndpointRep readForRootById(@PathVariable Long id,
                                              HttpServletRequest request) {
        return securityProfileService.readById(id, request);
    }

    @PutMapping("root/{id}")
    public ResponseEntity<?> replaceForRootById(@RequestBody UpdateBizEndpointCommand command, @PathVariable Long id, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        securityProfileService.replace(command, id, changeId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("root/{id}")
    public ResponseEntity<?> deleteForRootById(@PathVariable Long id, HttpServletRequest request) {
        securityProfileService.delete(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("admin")
    public ResponseEntity<?> deleteForAdminByQuery(@RequestParam(value = HTTP_PARAM_QUERY, required = false) String queryParam) {
        securityProfileService.deleteByQuery(queryParam);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "root/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<?> patchForRootById(@PathVariable(name = "id") Long id, @RequestBody JsonPatch patch, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId) {
        securityProfileService.patchById(id, patch, changeId);
        return ResponseEntity.ok().build();
    }
}
