package com.confluenciacreativa.fundamentos.caseuse;

import com.confluenciacreativa.fundamentos.entity.User;
import java.util.List;

public interface GetUser {
    List<User> getAll();
}
