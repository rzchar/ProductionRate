/**
 * Created by qyd on 2018/4/20.
 */
var err = function (data) {
    console.error(data.toString());
}

var commitURLList = [];

function getCommitList() {
    var pickOutCommitHash = function (data) {
        var commitList = data;
        $('#result').html('<ul id="commitList"></ul>');
        for (var d in data) {
            var commitInfo = data[d];
            $('#commitList').append('<li>' + data[d]['url'] + '</li>')
        }
    };
    console.log('here we go');
    $.ajax({
        url: 'input/commitList.json',
        type: 'get',
        success: function (data) {
            pickOutCommitHash(data);
        },
        error: function (data) {
            err(data);
        },
        dataType: 'json'
    });
}