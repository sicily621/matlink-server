package com.clt.matlink.modules.account.controller;

import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.account.domain.entity.Account;
import com.clt.matlink.modules.account.domain.form.AccountForm;
import com.clt.matlink.modules.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 账单
 */
@RequestMapping("/account")
@RestController
public class AccountController {
    @Autowired
    private AccountService accountService;
    /**
     * 新建账单
     */
    @PostMapping()
    public Result<Account> create(@RequestBody Account account){
        return Result.success(accountService.save(account));
    }
    /**
     * 修改账单
     * @param account
     * @return
     */
    @PutMapping()
    public Result<Account> update(@RequestBody Account account){
        return Result.success(accountService.save(account));
    }
    /**
     * 根据账单Id查询账单
     */
    @GetMapping("{id}")
    public Result<Account> getById(@PathVariable("id") Long id){
        return Result.success(accountService.getById(id));
    }
    /**
     * 根据订单Id查询账单
     */
    @GetMapping("/getByOrderId/{orderId}")
    public Result<List<Account>> getByOrderId(@PathVariable("orderId") Long orderId){
        return Result.success(accountService.getByOrderId(orderId));
    }
    /**
     * 根据账单Ids查询账单列表
     */
    @GetMapping("/getByIds/{ids}")
    public Result<List<Account>> getById(@PathVariable("ids") List<Long> ids){
        return Result.success(accountService.getByIds(ids));
    }

    /**
     * 删除账单
     */
    @DeleteMapping("{id}")
    public Result<Void> deleteById(@PathVariable("id") Long id){
        accountService.deleteById(id);
        return Result.success();
    }
    /**
     * 查询账单列表
     */
    @GetMapping("/list")
    public Result<List<Account>> list(){
        return Result.success(accountService.list());
    }

    /**
     * 分页查询账单列表
     */
    @GetMapping("/page")
    public Result<PageInfo<Account>> page(AccountForm accountForm){
        return Result.success(accountService.page(accountForm));
    }
    /**
     * 批量生成账单
     */
    @PostMapping("batchSave")
    public Result<Boolean> batchSave(@RequestBody List<Account> Accounts){
        accountService.batchSave(Accounts);
        return Result.success();
    }
}
