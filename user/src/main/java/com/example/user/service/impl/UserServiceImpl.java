package com.example.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.exception.OtherException;
import com.example.model.User;
import com.example.user.help.JwtHelper;
import com.example.user.mapper.UserMapping;
import com.example.user.service.UserService;
import com.example.utils.ResultEnum;
import com.example.vo.LoginCodeVo;
import com.example.vo.LoginPwVo;
import com.example.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapping, User> implements UserService {

    @Autowired
    private UserMapping userMapping;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    //用户可利用验证码登录，可以不设置密码，每次都利用验证码登录，也可以设置密码，利用密码登录
    @Override
    public Map<String, Object> loginCode(LoginCodeVo loginCodeVo) {
        Map<String, Object> map = new HashMap<>();
        String phone = loginCodeVo.getPhone();
        String code = loginCodeVo.getCode();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(code)){
            throw new OtherException(ResultEnum.LOGIN_FAIL);
        }
        //判断验证码是否正确
        String redisCode = redisTemplate.opsForValue().get(phone);
        if (!redisCode.equals(code)){
            throw new OtherException(ResultEnum.CODE_FAIL);
        }
        User user = byPhone(phone);
        //用户不存在，注册
        if (user == null){
            user = new User();
            System.out.println("user:"+user);
            System.out.println("id:"+user.getId());
            user.setPhone(phone);
            user.setUsername(phone);
            int insert = baseMapper.insert(user);
            if (insert <= 0){
                throw new OtherException(ResultEnum.FAIL);
            }
        }
        map.put("username", user.getUsername());
        //返回token信息
        String token = JwtHelper.createToken(user.getId(), user.getUsername());
        map.put("token",token);
        return map;
    }
    //用户利用密码登录（不能注册）
    @Override
    public Map<String, Object> loginPw(LoginPwVo loginPwVo) {
        String phone = loginPwVo.getPhone();
        String password = loginPwVo.getPassword();
        if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)){
            throw new OtherException(ResultEnum.LOGIN_ERROR);
        }
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("phone",phone);
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        wrapper.eq("password",password);
        User user = baseMapper.selectOne(wrapper);
        if (user == null){
            throw new OtherException(ResultEnum.FAIL);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("username",user.getUsername());
        String token = JwtHelper.createToken(user.getId(), user.getUsername());
        map.put("token",token);
        return map;
    }

    //查询电话号码用户是否存在
    @Override
    public User byPhone(String phone) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("phone",phone);
        return userMapping.selectOne(wrapper);
    }

    @Override
    public void update(Integer id,UserVo userVo) {
        User user = baseMapper.selectById(id);
        String username = userVo.getUsername();
        if (!StringUtils.isEmpty(username)){
            user.setUsername(username);
        }
        int sex = userVo.getSex();
        if (sex == 0 || sex == 1){
            user.setSex(sex);
        }
        int age = userVo.getAge();
        if (age > 0){
            user.setAge(age);
        }
        int i = baseMapper.updateById(user);
        if (i <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

    @Override
    public String writePw(HttpServletRequest request) {
        String token = request.getHeader("token");
        Integer userId = JwtHelper.getUserId(token);
        //userId = 1;
        User user = baseMapper.selectById(userId);
        String password = user.getPassword();
        System.out.println("password:"+password);
        return password;
    }

    @Override
    public void setPw(HttpServletRequest request, String pwd) {
        String token = request.getHeader("token");
        Integer userId = JwtHelper.getUserId(token);
        //userId = 1;
        User user = baseMapper.selectById(userId);
        pwd = DigestUtils.md5DigestAsHex(pwd.getBytes());
        user.setPassword(pwd);
        int i = baseMapper.updateById(user);
        if (i <= 0){
            throw new OtherException(ResultEnum.FAIL);
        }
    }

}
