package com.clt.matlink.modules.account.service;

import com.clt.matlink.common.domain.vo.PageInfo;
import com.clt.matlink.common.domain.vo.Result;
import com.clt.matlink.modules.account.domain.entity.Account;
import com.clt.matlink.modules.account.domain.form.AccountForm;

import java.util.List;

public interface AccountService {
    Account save(Account account);

    Account getById(Long id);

    List<Account> getByIds(List<Long> ids);

    Boolean deleteById(Long id);

    List<Account> list();

    PageInfo<Account> page(AccountForm accountForm);

    List<Account> getByOrderId(Long orderId);

    Result<Boolean> batchSave(List<Account> accounts);
}
