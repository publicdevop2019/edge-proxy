package com.hw.aggregate.endpoint;

import com.github.fge.jsonpatch.JsonPatch;
import com.hw.aggregate.endpoint.command.RootCreateBizEndpointCommand;
import com.hw.aggregate.endpoint.command.RootUpdateBizEndpointCommand;
import com.hw.aggregate.endpoint.representation.RootBizEndpointCardRep;
import com.hw.aggregate.endpoint.representation.RootBizEndpointRep;
import com.hw.config.InternalForwardHelper;
import com.mt.common.sql.SumPagedRep;
import com.mt.common.validation.BizValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

import static com.mt.common.AppConstant.*;

@RestController
@RequestMapping(produces = "application/json", path = "proxy/endpoints")
public class BizEndpointController {

    @Autowired
    RootBizEndpointApplicationService rootBizEndpointApplicationService;
    @Autowired
    InternalForwardHelper internalForwardHelper;
    @Autowired
    BizValidator validator;
    @PostMapping("root")
    public ResponseEntity<Void> createForRoot(@RequestBody RootCreateBizEndpointCommand command, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        validator.validate("rootCreateBizEndpointCommand",command);
        internalForwardHelper.forwardCheck(request);
        return ResponseEntity.ok().header("Location", String.valueOf(rootBizEndpointApplicationService.create(command, changeId).getId())).build();
    }

    @GetMapping("root")
    public ResponseEntity<SumPagedRep<RootBizEndpointCardRep>> readForRootByQuery(@RequestParam(value = HTTP_PARAM_QUERY, required = false) String queryParam,
                                                                                  @RequestParam(value = HTTP_PARAM_PAGE, required = false) String pageParam,
                                                                                  @RequestParam(value = HTTP_PARAM_SKIP_COUNT, required = false) String config, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        return ResponseEntity.ok(rootBizEndpointApplicationService.readByQuery(queryParam, pageParam, config));
    }

    @GetMapping("root/{id}")
    public ResponseEntity<RootBizEndpointRep> readForRootById(@PathVariable Long id,
                                                              HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        return ResponseEntity.ok(rootBizEndpointApplicationService.readById(id));
    }

    @PutMapping("root/{id}")
    public ResponseEntity<Void> replaceForRootById(@RequestBody RootUpdateBizEndpointCommand command, @PathVariable Long id, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        validator.validate("rootUpdateBizEndpointCommand",command);
        internalForwardHelper.forwardCheck(request);
        rootBizEndpointApplicationService.replaceById(id, command, changeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("root/{id}")
    public ResponseEntity<Void> deleteForRootById(@PathVariable Long id, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        rootBizEndpointApplicationService.deleteById(id, changeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("root")
    public ResponseEntity<Void> deleteForAdminByQuery(@RequestParam(value = HTTP_PARAM_QUERY, required = false) String queryParam, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        rootBizEndpointApplicationService.deleteByQuery(queryParam, changeId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping(path = "root/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<Void> patchForRootById(@PathVariable(name = "id") Long id, @RequestBody JsonPatch patch, @RequestHeader(HTTP_HEADER_CHANGE_ID) String changeId, HttpServletRequest request) {
        internalForwardHelper.forwardCheck(request);
        HashMap<String, Object> params = new HashMap<>();
        params.put(HTTP_HEADER_CHANGE_ID, changeId);
        rootBizEndpointApplicationService.patchById(id, patch, params);
        return ResponseEntity.ok().build();
    }
}
