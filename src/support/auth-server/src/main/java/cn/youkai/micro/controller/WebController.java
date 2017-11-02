package cn.youkai.micro.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author youkai
 * @date 2017/10/30.
 */
@RestController
@RequestMapping("/foo")
public class WebController {
//    @PreAuthorize("hasAuthority('FOO_READ')")
    @GetMapping
    public String readFoo() {
        return "read foo " + UUID.randomUUID().toString();
    }

//    @PreAuthorize("hasAuthority('FOO_WRITE')")
    @PostMapping
    public String writeFoo() {
        return "write foo " + UUID.randomUUID().toString();
    }
}
