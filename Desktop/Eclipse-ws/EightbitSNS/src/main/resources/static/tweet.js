// ツイート投稿フォームの表示トグル
// ツイートボタンをクリックしたときの処理
document.getElementById('toggleTweetForm').addEventListener('click', function() {
    // ツイート投稿フォームを表示または非表示にする
    var tweetForm = document.getElementById('tweetForm');
    if (tweetForm.style.display === 'none') {
        tweetForm.style.display = 'block';
        // ツイート投稿フォームまでスクロール
        tweetForm.scrollIntoView({ behavior: 'smooth' });
    } else {
        tweetForm.style.display = 'none';
    }
});




$('.retweet-button').click(function(e) {
    e.stopPropagation(); // イベントのバブリングを停止する
    retweetInModal(); // リツイート処理を実行
});





$('.like-button').click(function(event) {
    // イベントのバブリングを停止する
    event.stopPropagation();
    // イベントのデフォルト動作（通常のモーダル表示）を無効にする
    event.preventDefault();
});







function likeTweet(event, button) {
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

            // モーダル内のいいね数も更新する
            var modalLikesCountElement = document.querySelector(`#tweetDetailModal_${tweetId} .likes-count`);
            if (modalLikesCountElement) {
                modalLikesCountElement.innerText = response.likes;
            }
        },
        error: function(xhr, status, error) {
            console.error("エラーが発生しました:", error);
            console.error("ステータスコード:", xhr.status);
            console.error("レスポンステキスト:", xhr.responseText);
            alert("エラーが発生しました。もう一度お試しください。");
        }
    });
}




function likeTweetInModal(event, button) {
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
            if (likesCountElement) {
                likesCountElement.innerText = response.likes;
            }

            // ボタンのテキストやスタイルを更新して、いいねの状態を反映する
            if (button.classList.contains('liked')) {
                button.classList.remove('liked');
            } else {
                button.classList.add('liked');
            }

            // モーダル外のいいね数も更新する
            var tweetId = button.parentNode.querySelector('[name="tweetId"]').value;
            var modalLikesCountElement = document.querySelector(`#tweetDetailModal_${tweetId} .likes-count`);
            if (modalLikesCountElement) {
                modalLikesCountElement.innerText = response.likes;
            }
        },
        error: function(xhr, status, error) {
            console.error("エラーが発生しました:", error);
            console.error("ステータスコード:", xhr.status);
            console.error("レスポンステキスト:", xhr.responseText);
            alert("エラーが発生しました。もう一度お試しください。");
        }
    });
}


function confirmDelete(event) {
    var button = event.currentTarget;
    var tweetIdString = button.getAttribute('data-tweet-id');
    
    // 文字列から整数に変換
    var tweetId = parseInt(tweetIdString);

    // tweetIdがNaN（Not a Number）でないことを確認する
    if (!isNaN(tweetId)) {
        if (confirm("本当に削除しますか？")) {
            $.ajax({
                url: '/delete',
                type: 'POST',
                data: { tweetId: tweetId },
                success: function(response) {
    			window.location.href = "/";
                },
                error: function(xhr, status, error) {
                    console.error(xhr.responseText);
                    alert("ツイートの削除中にエラーが発生しました。");
                }
            });
        }
    } else {
        alert("不正なツイートIDです。");
    }
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






function openRetweetModal(tweetId) {
    // モーダルを表示するロジックを追加
    $("#retweetModal").modal("show");

    // モーダル内のツイートIDを設定
    $("#retweetModal").find("input[name='tweetId']").val(tweetId);
}

function retweetInModal() {
    var modal = $("#retweetModal");
    var tweetId = modal.find("input[name='tweetId']").val();
    var userId = modal.find("input[name='userId']").val();

    // Ajaxリクエスト
    $.ajax({
        type: "POST",
        url: "/retweet",
        data: { tweetId: tweetId, userId: userId },
        success: function(response) {
            // モーダルを閉じる
            $("#retweetModal").modal("hide");
            // 成功した場合、ページをリロードしてリツイートされたツイートを表示
            window.location.reload();
        },
        error: function(xhr, status, error) {
            console.error("エラーが発生しました:", error);
            console.error("ステータスコード:", xhr.status);
            console.error("レスポンステキスト:", xhr.responseText);
            alert("エラーが発生しました。もう一度お試しください。");
        }
    });
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
    // userIdをdata属性から取得
    var userId = $(element).find("a").data("userid");
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




