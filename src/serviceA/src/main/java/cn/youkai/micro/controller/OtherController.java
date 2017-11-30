package cn.youkai.micro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author youkai
 * @date 2017/11/30.
 */
@RestController
@RequestMapping("/other")
public class OtherController {
    @GetMapping
    public String readOther() {
        return "read other " + UUID.randomUUID().toString();
    }
}
