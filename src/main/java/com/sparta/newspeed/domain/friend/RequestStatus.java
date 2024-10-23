package com.sparta.newspeed.domain.friend;

import lombok.Getter;

@Getter
public enum RequestStatus {
    ACCEPTED, //친구 관계 성립
    PENDING, //친구 요청 대기 중
    DELETED //친구 관계 중단 (탈퇴로 비활성화 상태)
}
