package edu.hut.bookshop.controller;

import com.github.pagehelper.Page;

import com.github.pagehelper.PageInfo;
import edu.hut.bookshop.dao.UserMapper;
import edu.hut.bookshop.pojo.Order;
import edu.hut.bookshop.pojo.User;
import edu.hut.bookshop.service.UserService;
import edu.hut.bookshop.util.ResultCode;
import edu.hut.bookshop.util.ResultVO;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Description: 用户管理模块控制器
 * @Author: chenjianchi
 * @Date: 2020/6/2 2:04
 */
@RestController
@RequestMapping("/user")
public class UserController {
   @Autowired
   private UserService userService;
    /**
     * 分页获取用户列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/list")
    public ResultVO getUserList(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer limit) {
        List<User> users=  userService.selectAll(page,limit);
        PageInfo pageInfo = new PageInfo(users);
        return new ResultVO(ResultCode.SUCCESS,(int)pageInfo.getTotal(),users);
    }

    /**
     * 删除指定用户ID
     * @param userId
     * @return
     */
    @DeleteMapping("/list/{userId}")
    public ResultVO deleteUser(@PathVariable("userId") Integer userId) {
        int users =userService.deleteByUserId(userId);
        return new ResultVO(ResultCode.SUCCESS,null);
    }



    /**
     * 修改    添加验证注解和RequestBody注解
     * @param record
     * @return
     */
    @PostMapping("/update")
    public ResultVO updateUser(@RequestBody @Valid User record) {
        int users =userService.updateByUserId(record);
        return new ResultVO(ResultCode.SUCCESS,null);
    }

    @GetMapping("/search")
    public ResultVO searchUsers(User user, Integer page, Integer limit) {
        if (user.getUserName().isEmpty()) {
            user.setUserName(null);
        }
        if (user.getEmail().isEmpty()) {
            user.setEmail(null);
        }
        List<User> users = userService.searchUsers(user, page, limit);
        PageInfo pageInfo = new PageInfo(users);
        return new ResultVO(ResultCode.SUCCESS, (int) pageInfo.getTotal(), users);
    }

}
