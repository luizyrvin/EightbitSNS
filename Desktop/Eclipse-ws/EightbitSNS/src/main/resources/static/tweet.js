// ツイート投稿フォームの表示トグル
document.getElementById("toggleTweetForm").addEventListener("click", function() {
    var tweetForm = document.getElementById("tweetForm");
    if (tweetForm.style.display === "none") {
        tweetForm.style.display = "block";
    } else {
        tweetForm.style.display = "none";
    }
});


// いいねボタンがクリックされたときの処理
   $('.like-button').click(function(event) {
    event.preventDefault();

var tweetId = document.getElementById('tweetId').innerText;
    var userId = $(this).closest('.card').find('[name="userId"]').val();
    
    var likesCount = parseInt($('#likes-count-' + tweetId).text());
	$('#likes-count-' + tweetId).text(likesCount + 1);
    
    $.ajax({
        type: 'POST',
        url: '/like',
        data: {
            tweetId: tweetId,
            userId: userId
        },
        success: function(data) {
        },
        error: function(xhr, status, error) {
            console.error('いいねの処理中にエラーが発生しました: ' + xhr.responseText);
        }
    });
});


$('.tweet-link').click(function(e){
    e.preventDefault(); // リンクのデフォルトの挙動を無効化
    var tweetId = $(this).data('tweet-id'); // クリックされたツイートのIDを取得
    var tweetUserName = $(this).find('.tweet-username').text(); // ツイートのユーザー名を取得
    var tweetUserId = $(this).find('.tweet-userid').text(); // ツイートのユーザーIDを取得
    var tweetText = $(this).find('.tweet-text').text(); // ツイートの内容を取得

    // モーダル内の要素に取得した情報を設定
    $('#tweetId').text(tweetId);
    $('#tweetUserName').text(tweetUserName);
    $('#tweetUserId').text(tweetUserId);
    $('#tweetText').text(tweetText);

    // モーダルを表示
    $('#tweetModal').modal('show');
});
