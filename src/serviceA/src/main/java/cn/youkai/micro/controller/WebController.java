package cn.youkai.micro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
import java.util.UUID;

/**
 * @author youkai
 * @date 2017/11/28.
 */
@RestController
@RequestMapping("/sva")
public class WebController {
    @GetMapping
    public String readSva() {
        return "read sva " + UUID.randomUUID().toString();
    }
}
