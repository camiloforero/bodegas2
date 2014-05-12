define(['model/_logModel'], function() {
    App.Model.LogModel = App.Model._LogModel.extend({

    });

    App.Model.LogList = App.Model._LogList.extend({
        model: App.Model.LogModel
    });

    return  App.Model.LogModel;

});