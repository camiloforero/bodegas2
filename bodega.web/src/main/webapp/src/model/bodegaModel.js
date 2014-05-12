define(['model/_bodegaModel'], function() {
    App.Model.BodegaModel = App.Model._BodegaModel.extend({

    });

    App.Model.BodegaList = App.Model._BodegaList.extend({
        model: App.Model.BodegaModel
    });

    return  App.Model.BodegaModel;

});