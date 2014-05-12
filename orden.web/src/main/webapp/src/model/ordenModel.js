define(['model/_ordenModel'], function() {
    App.Model.OrdenModel = App.Model._OrdenModel.extend({

    });

    App.Model.OrdenList = App.Model._OrdenList.extend({
        model: App.Model.OrdenModel
    });

    return  App.Model.OrdenModel;

});