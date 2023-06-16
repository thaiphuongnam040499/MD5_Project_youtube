package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rikkei.academy.dto.request.SubscribeDTO;
import rikkei.academy.model.Subscribe;
import rikkei.academy.service.channelService.IChannelService;
import rikkei.academy.service.subscribeService.ISubscribeService;
import rikkei.academy.service.userService.IUserService;

import java.util.Optional;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {
    @Autowired
    private ISubscribeService subscribeService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IChannelService channelService;

    @GetMapping("/{channelId}")
    public ResponseEntity<?> getSubscribeByChannelId(@PathVariable Long channelId) {
        return ResponseEntity.ok(subscribeService.countSubscribeByChannelId(channelId));
    }

    @PostMapping("/createSubscribe")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<?> createSubscribe(@RequestBody SubscribeDTO subscribe) {
        Optional<Subscribe> check = subscribeService.findSubscribeByChannelIdAndUserId(subscribe.getUserId(), subscribe.getChannelId());
        if (check.isPresent()){
            return ResponseEntity.ok("Đã đăng kí trước đó");
        }
        Subscribe newSubscribe = new Subscribe();
        newSubscribe.setUser(userService.findById(subscribe.getUserId()));
        newSubscribe.setChannel(channelService.findById(subscribe.getChannelId()));
        return ResponseEntity.ok(subscribeService.save(newSubscribe));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteSubscribe(@PathVariable Long id) {
        subscribeService.delete(id);
    }
}
