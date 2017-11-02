package cn.youkai.micro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author youkai
 * @date 2017/10/31.
 */
@RestController
@RequestMapping("/bar")
public class PublicController {
    @GetMapping
    public String readBar() {
        return "read bar " + UUID.randomUUID().toString();
    }
}
