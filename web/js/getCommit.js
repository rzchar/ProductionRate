/**
 * Created by qyd on 2018/4/20.
 */
var err = function (data) {
    console.error(data.toString());
}

function getCommitList() {
    var pickOutCommitHash = function (data) {
        var commitList = data;
        for (var d in data) {

        }
    };
    console.log('here we go');
    $.ajax({
        url: "./input/commitList.json",
        success: function (data) {
            pickOutCommitHash(data)
        },
        error: function (data) {
            err(data)
        },
        dataType: 'json'
    });
}