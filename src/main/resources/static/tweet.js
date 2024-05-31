// ツイート投稿フォームの表示トグル
document.getElementById("toggleTweetForm").addEventListener("click", function() {
    var tweetForm = document.getElementById("tweetForm");
    if (tweetForm.style.display === "none") {
        tweetForm.style.display = "block";
    } else {
        tweetForm.style.display = "none";
    }
});

$('.like-button').click(function(e) {
    e.stopPropagation(); // イベントのバブリングを停止する
    likeTweet(event, this); // いいね処理を実行
});


$('.retweet-button').click(function(e) {
    e.stopPropagation(); // イベントのバブリングを停止する
    retweetInModal(); // リツイート処理を実行
});









function likeTweet(event, button) {
    event.preventDefault();
    var userId = button.parentNode.querySelector('[name="userId"]').value;
    var tweetId = button.parentNode.querySelector('[name="tweetId"]').value;

    // Ajaxリクエスト
    $.ajax({
        type: "POST",
        url: "/likeTweet",
        data: { userId: userId, tweetId: tweetId },
        success: function(response) {
            // 成功した場合、いいね数を更新
            var likesCountElement = button.parentNode.querySelector('.likes-count');
            likesCountElement.innerText = response.likes;

            // ボタンのテキストやスタイルを更新して、いいねの状態を反映する
            if (button.classList.contains('liked')) {
                button.classList.remove('liked');
            } else {
                button.classList.add('liked');
            }
        },
        error: function(xhr, status, error) {
            console.error("エラーが発生しました:", error);
            console.error("ステータスコード:", xhr.status);
            console.error("レスポンステキスト:", xhr.responseText);
        }
    });
}


function commentTweet(button) {
    var tweetId = button.parentNode.querySelector('[name="tweetId"]').value;
    var userId = document.getElementById("commentUserId").value; // コメントフォームのユーザーIDを取得
    
    // Ajaxリクエスト
    $.ajax({
        type: "POST",
        url: "/commentTweet",
        data: { tweetId: tweetId, userId: userId }, // ログインユーザーのIDをリクエストに含める
        success: function(response) {
            // 成功した場合、コメント数を更新
            var commentsCountElement = button.parentNode.querySelector('.comments-count');
            commentsCountElement.innerText = response.comments;
        },
        error: function(xhr, status, error) {
            console.error("エラーが発生しました:", error);
            console.error("ステータスコード:", xhr.status);
            console.error("レスポンステキスト:", xhr.responseText);
        }
    });
}




//リツイートボタンでリツイートモーダル呼び出し
function openRetweetModal(tweetId) {
    // tweetIdを使ってモーダルを開く処理を記述する
    $('#retweetModal').modal('show');
}





/*
$(document).ready(function() {
    $('.modal-body').on('click', '.like-button', function() {
        likeTweet(this);
    });
});
*/


function openTweetDetail(element) {
    var username = $(element).find(".card-title").text();
	var userId = $(element).find("[name='userId']").val();
    var tweetText = $(element).find(".tweet-content").text();
    var likes = $(element).find(".likes-count").text();
    var tweetId = $(element).find("[name='tweetId']").val();

    // モーダル内の要素を更新
    $("#tweetDetailModal_" + tweetId).find("#tweetDetailUsername").text(username);
    $("#tweetDetailModal_" + tweetId).find("#tweetDetailUserId").text(userId);
    $("#tweetDetailModal_" + tweetId).find("#tweetDetailText").text(tweetText);
    $("#tweetDetailModal_" + tweetId).find("#tweetDetailLikes").text(likes);
    
    // コメントフォームのuserIdとtweetIdを更新
    $("#tweetDetailModal_" + tweetId).find("#commentForm #userId").val(userId);
    $("#tweetDetailModal_" + tweetId).find("#commentForm #tweetId").val(tweetId);

    // モーダルを表示
    $("#tweetDetailModal_" + tweetId).modal("show");
    console.log("userIdの値:", userId);

}


function openCommentFormModal(event, formId) {
    event.preventDefault(); // デフォルトの動作をキャンセル

    // モーダルのIDを生成
    var modalId = 'tweetDetailModal_' + formId.split('_')[1];

    // モーダルを表示
	$('#tweetDetailModal_' + tweet.getTweetId()).modal('show');

    // モーダル内のテキストエリアにフォーカスを当てる
    $('#' + modalId + ' textarea[name="text"]').focus();
}





/*
function commentInModal() {
    // コメント入力フォームからコメントを取得
    var commentText = $("#commentText").val();

    // コメント表示エリアにコメントを追加
    $("#commentArea").append("<p>" + commentText + "</p>");

    // コメント入力フォームをクリア
    $("#commentText").val("");
}
*/




/*

function submitComment() {
    // コメントフォームからコメントテキストを取得
    var commentText = document.getElementById("commentText").value;
    // ユーザーIDとツイートIDを取得
    var userId = document.getElementById("userId").value;
    var tweetId = document.getElementById("tweetId").value;

    // コメントデータをオブジェクトにまとめる
    var commentData = {
        userId: userId,
        tweetId: tweetId,
        text: commentText
    };

    // Ajaxリクエストを作成
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/comment", true);
    xhr.setRequestHeader("Content-Type", "application/json");

    // レスポンスの受信を処理するコールバック関数
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // コメントが正常に送信された場合の処理
            // ここに適切な処理を記述してください
        }
    };

    // コメントデータをJSON形式に変換して送信
    xhr.send(JSON.stringify(commentData));
}

*/


/*無限言い値だったら動くコード @PostMapping("/likeTweet")
function likeTweet(button) {
    var userId = button.parentNode.querySelector('[name="userId"]').value;
    var tweetId = button.parentNode.querySelector('[name="tweetId"]').value;
    
    // Ajaxリクエスト
    $.ajax({
        type: "POST",
        url: "/likeTweet",
        data: { userId: userId, tweetId: tweetId },
        success: function(response) {
            // 成功した場合、いいね数を更新
            var likesCountElement = button.parentNode.querySelector('.likes-count');
            likesCountElement.innerText = response.likes;
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });
}
*/







