package com.electrica.Electrica.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResoponse {
    String message;
    Boolean status;
    String type;
    Integer id;
}
