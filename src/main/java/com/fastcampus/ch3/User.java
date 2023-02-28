package com.fastcampus.ch3;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private String id;
    private String pwd;
    private String name;
    private String email;
    private Date birth; // java.util.Date
    private String sns;
    private Date reg_date;
}
