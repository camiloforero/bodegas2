define(['model/_itemInventarioModel'], function() {
    App.Model.ItemInventarioModel = App.Model._ItemInventarioModel.extend({

    });

    App.Model.ItemInventarioList = App.Model._ItemInventarioList.extend({
        model: App.Model.ItemInventarioModel
    });

    return  App.Model.ItemInventarioModel;

});