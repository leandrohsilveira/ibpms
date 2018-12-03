package com.github.leandrohsilveira.ibpms.user;

import lombok.*;

@Data
@NoArgsConstructor
public class User {

    final long id = System.currentTimeMillis();

    @NonNull
    String name;
}
