
# API LIST

| HTTP Method | API Endpoint | 概要 | 説明 |
| :--- | :--- | :--- | :--- |
| `PUT` | `/admin/ban` | ユーザー停止 | ユーザーを停止させます。 |
| `PUT` | `/admin/unban` | ユーザー停止解除 | ユーザーの停止を解除します。 |
| `GET` | `/admin/users` | ユーザーリスト照会 | ユーザーリストを照会します。 |
| `DELETE` | `/admin/user` | ユーザー退会 | ユーザーを退会させます。 |
| `POST` | `/auth/login` | ログイン | ログインします。 |
| `POST` | `/auth/sign-up` | 会員登録 | 会員登録をします。 |
| `GET` | `/matches` | マッチ投稿リスト照会 | マッチ投稿リストを照会します。 |
| `POST` | `/matches` | マッチ投稿作成 | 新しいマッチ投稿を作成します。 |
| `GET` | `/matches/{postId}` | マッチ投稿照会 | 特定のマッチ投稿を照会します。 |
| `DELETE` | `/matches/{postId}` | マッチ投稿削除 | 特定のマッチ投稿を削除します。 |
| `PUT` | `/matches` | マッチ投稿修正 | マッチ投稿を修正します。 |
| `GET` | `/matches/host` | 主催したマッチリスト照会 | 自分が主催したマッチリストを照会します。 |
| `GET` | `/matches/participate` | 参加したマッチリスト照会 | 自分が参加したマッチリストを照会します。 |
| `GET` | `/matches/{teamId}/host` | 特定チームの主催マッチリスト照会 | 特定チームが主催したマッチリストを照会します。 |
| `GET` | `/matches/requests` | 受信したマッチ申請リスト照会 | 受信したマッチ申請リストを照会します。 |
| `GET` | `/matches/requests/{matchId}/detail` | 受信したマッチ申請詳細照会 | 受信したマッチ申請の詳細情報を照会します。 |
| `POST` | `/matches/{postId}/request` | マッチ参加申請 | マッチに参加を申請します。 |
| `POST` | `/matches/confirm` | マッチ申請承諾 | マッチ申請を承諾します。 |
| `DELETE` | `/matches/cancel` | マッチ申請キャンセル | マッチ申請をキャンセルします。 |
| `DELETE` | `/matches/cancel/confirm` | 確定済みマッチのキャンセル | 確定したマッチをキャンセルします。 |
| `DELETE` | `/matches/refuse` | マッチ申請拒否 | マッチ申請を拒否します。 |
| `POST` | `/matches/rate` | マナー点数評価 | マナー点数を評価します。 |
| `POST` | `/notification/subscribe` | プッシュ通知購読 | プッシュ通知を購読します。 |
| `POST` | `/teams` | チーム登録 | チームを登録します。 |
| `DELETE` | `/teams` | チーム削除 | チームを削除します。 |
| `POST` | `/teams/leader/accept` | リーダー変更承諾 | リーダー変更申請を承諾します。 |
| `POST` | `/teams/leader/refuse` | リーダー変更拒否 | リーダー変更申請を拒否します。 |
| `POST` | `/teams/leader` | リーダー変更申請 | リーダーの変更を申請します。 |
| `GET` | `/teams/profile` | チームプロフィール照会 | チームプロフィールを照会します。 |
| `PUT` | `/teams/profile` | チームプロフィール修正 | チームプロフィールを修正します。 |