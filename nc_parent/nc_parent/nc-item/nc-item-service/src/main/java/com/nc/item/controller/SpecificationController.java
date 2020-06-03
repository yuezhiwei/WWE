package com.nc.item.controller;

import com.nc.item.pojo.SpecGroup;
import com.nc.item.pojo.SpecParam;
import com.nc.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> specGroups = specificationService.queryGroupsByCid(cid);
        if (CollectionUtils.isEmpty(specGroups)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(specGroups);
    }


    @RequestMapping("group")
    public ResponseEntity<Void> saveOrUpdateGroup(@RequestBody SpecGroup specGroup){
        specificationService.saveOrUpdateGroup(specGroup);
        return ResponseEntity.ok().build();
    }
    @RequestMapping("group/{id}")
    public ResponseEntity<Void> deleteGroupById(@PathVariable("id") Long id){
        specificationService.deleteGroupById(id);
        return ResponseEntity.ok().build();
    }




    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParams(
            @RequestParam(value = "gid", required = false)Long gid,
            @RequestParam(value = "cid", required = false)Long cid,
            @RequestParam(value = "generic", required = false)Boolean generic,
            @RequestParam(value = "searching", required = false)Boolean searching
    ){

        List<SpecParam> params = this.specificationService.queryParams(gid, cid, generic, searching);

        if (CollectionUtils.isEmpty(params)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(params);
    }

    @RequestMapping("param")
    public ResponseEntity<Void> saveOrUpdateParam(@RequestBody SpecParam specParam){
        specificationService.saveOrUpdateParam(specParam);
        return ResponseEntity.ok().build();
    }
    @RequestMapping("Param/{id}")
    public ResponseEntity<Void> deleteParamById(@PathVariable("id") Long id){
        specificationService.deleteParamById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecsByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> list = this.specificationService.querySpecsByCid(cid);
        if(list == null || list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
}