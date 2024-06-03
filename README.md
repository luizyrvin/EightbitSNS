# EightbitSNS

## 概要
EightbitSNSは、社内コミュニケーションを活性化するためのSNS風Webアプリケーションです。<br>タイムラインには社員全員の投稿が表示され、ユーザーは投稿、コメント、いいね機能を使って情報を共有し、交流を深めることができます。<br><br><br><br>

## 主な機能
1. **ユーザー登録・ログイン**
   - アカウント作成機能：ユーザーはメールアドレスとパスワードを使用して新しいアカウントを作成できます。
   - ログイン機能：登録済みのユーザーはメールアドレスとパスワードを入力してアカウントにログインできます。
   <br><br>
2. **タイムライン表示**
   - 社内ユーザーの投稿をタイムラインで閲覧できます。
   - タイムラインには投稿の詳細表示、コメント、いいねが可能です。
   <br><br>
3. **投稿機能**
   - テキストツイートを投稿できます。
   - 投稿にはコメント、いいね機能が付属します。
   - 投稿主が自分である場合はツイートを削除することができます。
   <br><br>
4. **プロフィール管理**
   - 自分のプロフィールを表示し、編集できます。
   - プロフィールにはユーザー名、プロフィール画像、自己紹介文などが含まれます。
   <br><br>

5. **検索機能**
   - 検索バーからキーワードでツイートを検索することができます。
   - 他ユーザーのidをクリックするとプロフィール画面へと遷移し、そのユーザーだけの投稿を閲覧することができます。
     <br><br><br><br>
     
**機能一覧**

| ログイン機能 | ログアウト機能 |
|---------------|----------------|
|![login](https://github.com/luizyrvin/EightbitSNS/assets/171106589/08892e67-0da5-45fe-a32b-e92088c0e2e0)|![logout](https://github.com/luizyrvin/EightbitSNS/assets/171106589/1814cf57-4776-4f1a-ade7-0947b4b3a294)|
| メールアドレスとパスワードでログインを行う機能です。 | マイページからログアウトを行うための機能です。 |

| ユーザー登録機能 | プロフィール編集機能 |
|---------------|----------------|
|![register](https://github.com/luizyrvin/EightbitSNS/assets/171106589/e8a6f35c-2d0a-49ac-92f6-f7449b18464c)|![editProfile](https://github.com/luizyrvin/EightbitSNS/assets/171106589/437e8057-9a41-4207-b788-b33d18fc3287)|
|ユーザーの新規登録機能です。| プロフィール情報を更新するための機能です。<br> 動的なプレビューを表示することで変更後のアイコンも確認することができます。|

| ツイート機能 | ツイートへのアクション機能 |
|---------------|----------------|
|![tweet](https://github.com/luizyrvin/EightbitSNS/assets/171106589/653af62d-0d8d-4460-9357-74d5ef32993a)|![fav_comment](https://github.com/luizyrvin/EightbitSNS/assets/171106589/b5a3205c-1bc9-4de2-887a-4510d2c7bcbb)|
|ツイートをするための機能です。<br>画面左上のツイートボタンを押下すると投稿フォームが表示されます。| ツイートに対してのいいね・コメント機能です。<br>また、ツイートを押下するかコメントボタンを押すと詳細画面が開きます。|

| 検索機能 | ツイートの削除機能 |
|---------------|----------------|
|![search](https://github.com/luizyrvin/EightbitSNS/assets/171106589/79e42371-7167-4be3-8812-e154186e3523)|![delete](https://github.com/luizyrvin/EightbitSNS/assets/171106589/55e76592-5547-4da9-aec7-7d8f28ebfa80)|
|キーワードからツイートを検索する機能です。キーワードが入力されていない場合は「空欄です」と検索バーに表示します。ユーザーのプロフィール画面に遷移すると遷移先のユーザーのツイートのみ表示されます。|ツイートの削除機能です。ツイートに紐づいたコメントやいいねの履歴も連動して削除されます。|

| レスポンシブデザイン対応 |
|-------------------------|
|![responsive](https://github.com/luizyrvin/EightbitSNS/assets/171106589/1acc3148-2529-4fb7-8185-61ba4783e56b)|
|レスポンシブデザインに対応しているため、<br>スマートフォン表示でも操作性を損ないません。|

<br><br><br><br>

## MySQLセットアップ
以下のクエリをMySQLで実行してください。
 ```sql
CREATE DATABASE your_database;
USE your_database;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    tweet_user_id VARCHAR(255),
    username VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    profile_image_url VARCHAR(255),
    register_date DATETIME,
    last_login DATETIME,
    role VARCHAR(255) DEFAULT 'USER',
    introduction VARCHAR(255)
);

CREATE TABLE tweets (
    tweet_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    username VARCHAR(255),
    tweet_text VARCHAR(280) NOT NULL,
    media_file VARCHAR(255),
    post_date DATETIME,
    likes INT DEFAULT 0,
    retweets INT DEFAULT 0,
    comments INT DEFAULT 0,
    tweet_user_id VARCHAR(255),
    profile_image_url VARCHAR(255)
);

CREATE TABLE comments (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    tweet_id INT,
    user_id INT,
    text TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    username VARCHAR(255),
    tweet_user_id VARCHAR(255)
);

CREATE TABLE userlikes (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    tweet_id INT,
    liked TINYINT(1) DEFAULT 0,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (tweet_id) REFERENCES tweets(tweet_id)
);
 ```
<br><br><br><br>

## 使用技術
- **フロントエンド**
  ・HTML
  　　- Thymeleaf(テンプレートエンジン)
  ・ CSS
  　  - Bootstrap
  ・ JavaScript

- **バックエンド**
  ・ Java
     - Spring Boot

- **データベース**
  - MySQL

## 製作者・ライセンス等
