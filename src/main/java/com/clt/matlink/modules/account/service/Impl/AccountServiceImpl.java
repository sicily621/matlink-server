package com.clt.matlink.modules.account.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.common.enums.DelFlagEnum;
import com.clt.matlink.modules.account.domain.entity.Account;
import com.clt.matlink.modules.account.domain.form.AccountForm;
import com.clt.matlink.modules.account.mapper.AccountMapper;
import com.clt.matlink.modules.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountMapper accountMapper;
    @Override
    public Account save(Account account) {
        int flag = 0;
        if(account.getId()==null){
            flag= accountMapper.insert(account);
        }else{
            flag = accountMapper.updateById(account);
        }
        if(flag>0){
            return accountMapper.selectById(account.getId());
        }else{
            return null;
        }
    }

    @Override
    public Account getById(Long id) {
        return accountMapper.selectById(id);
    }

    @Override
    public List<Account> getByIds(List<Long> ids) {
        LambdaQueryWrapper<Account> lqw = Wrappers.lambdaQuery();
        lqw.eq(Account::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.in( Account::getId, ids);
        return accountMapper.selectList(lqw);
    }

    @Override
    public Boolean deleteById(Long id) {
        accountMapper.deleteById(id);
        return true;
    }

    @Override
    public List<Account> list() {
        LambdaQueryWrapper<Account> lqw = Wrappers.lambdaQuery();
        lqw.eq( Account::getDelFlag, DelFlagEnum.NORMAL.getValue());
        return accountMapper.selectList(lqw);
    }

    @Override
    public PageInfo<Account> page(AccountForm accountForm) {
        LambdaQueryWrapper<Account> lqw = Wrappers.lambdaQuery();
        lqw.ge(accountForm.getStartTime()!=null, Account::getCreateTime, accountForm.getStartTime());
        lqw.le(accountForm.getEndTime()!=null, Account::getCreateTime, accountForm.getEndTime());
        lqw.eq(accountForm.getOrderId()!=null, Account::getOrderId, accountForm.getOrderId());
        lqw.eq(accountForm.getType()!=null, Account::getType, accountForm.getType());
        lqw.eq(accountForm.getEmployeeId()!=null, Account::getEmployeeId, accountForm.getEmployeeId());
        lqw.eq(accountForm.getStatus()!=null, Account::getStatus, accountForm.getStatus());
        lqw.eq(accountForm.getRelatedEntityId()!=null, Account::getRelatedEntityId, accountForm.getRelatedEntityId());
        lqw.eq( Account::getDelFlag, DelFlagEnum.NORMAL.getValue());
        lqw.orderByDesc( Account::getId);
        Page<Account> page = accountForm.build();
        Page<Account> result = accountMapper.selectPage(page, lqw);
        PageInfo<Account> tableDataInfo = PageInfo.build(result);
        return tableDataInfo;
    }

    @Override
    public List<Account> getByOrderId(Long orderId) {
        LambdaQueryWrapper<Account> lqw = Wrappers.lambdaQuery();
        lqw.eq( Account::getOrderId, orderId);
        lqw.eq( Account::getDelFlag, DelFlagEnum.NORMAL.getValue());
        List<Account> result = accountMapper.selectList(lqw);
        return result;
    }

    @Override
    public Result<Boolean> batchSave(List<Account> accounts) {
        accountMapper.insertOrUpdateBatch(accounts);
        return Result.success();
    }
}
