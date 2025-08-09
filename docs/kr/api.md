# API LIST

| HTTP Method | API Endpoint | Summary | Description |
| :--- | :--- | :--- | :--- |
| `PUT` | `/admin/ban` | 유저 정지 | 유저를 정지시킵니다. |
| `PUT` | `/admin/unban` | 유저 정지 해제 | 유저 정지를 해제합니다. |
| `GET` | `/admin/users` | 유저 목록 조회 | 유저 목록을 조회합니다. |
| `DELETE` | `/admin/user` | 유저 탈퇴 | 유저를 탈퇴시킵니다. |
| `POST` | `/auth/login` | 로그인 | 로그인을 합니다. |
| `POST` | `/auth/sign-up` | 회원가입 | 회원가입을 합니다. |
| `GET` | `/matches` | 매치 게시글 목록 조회 | 매치 게시글 목록을 조회합니다. |
| `POST` | `/matches` | 매치 게시글 생성 | 새로운 매치 게시글을 생성합니다. |
| `GET` | `/matches/{postId}` | 매치 게시글 조회 | 특정 매치 게시글을 조회합니다. |
| `DELETE` | `/matches/{postId}` | 매치 게시글 삭제 | 특정 매치 게시글을 삭제합니다. |
| `PUT` | `/matches` | 매치 게시글 수정 | 매치 게시글을 수정합니다. |
| `GET` | `/matches/host` | 주최한 매치 목록 조회 | 내가 주최한 매치 목록을 조회합니다. |
| `GET` | `/matches/participate` | 참여한 매치 목록 조회 | 내가 참여한 매치 목록을 조회합니다. |
| `GET` | `/matches/{teamId}/host` | 특정 팀의 주최 매치 목록 조회 | 특정 팀이 주최한 매치 목록을 조회합니다. |
| `GET` | `/matches/requests` | 받은 매치 요청 목록 조회 | 받은 매치 요청 목록을 조회합니다. |
| `GET` | `/matches/requests/{matchId}/detail` | 받은 매치 요청 상세 조회 | 받은 매치 요청의 상세 정보를 조회합니다. |
| `POST` | `/matches/{postId}/request` | 매치 참여 요청 | 매치에 참여 요청을 보냅니다. |
| `POST` | `/matches/confirm` | 매치 요청 수락 | 매치 요청을 수락합니다. |
| `DELETE` | `/matches/cancel` | 매치 요청 취소 | 매치 요청을 취소합니다. |
| `DELETE` | `/matches/cancel/confirm` | 확정된 매치 취소 | 확정된 매치를 취소합니다. |
| `DELETE` | `/matches/refuse` | 매치 요청 거절 | 매치 요청을 거절합니다. |
| `POST` | `/matches/rate` | 매너 점수 평가 | 매너 점수를 평가합니다. |
| `POST` | `/notification/subscribe` | 푸시 알림 구독 | 푸시 알림을 구독합니다. |
| `POST` | `/teams` | 팀 등록 | 팀을 등록합니다. |
| `DELETE` | `/teams` | 팀 삭제 | 팀을 삭제합니다. |
| `POST` | `/teams/leader/accept` | 리더 변경 수락 | 리더 변경 요청을 수락합니다. |
| `POST` | `/teams/leader/refuse` | 리더 변경 거절 | 리더 변경 요청을 거절합니다. |
| `POST` | `/teams/leader` | 리더 변경 요청 | 리더 변경을 요청합니다. |
| `GET` | `/teams/profile` | 팀 프로필 조회 | 팀 프로필을 조회합니다. |
| `PUT` | `/teams/profile` | 팀 프로필 수정 | 팀 프로필을 수정합니다. |