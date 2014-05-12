define(['model/_pedidoModel'], function() {
    App.Model.PedidoModel = App.Model._PedidoModel.extend({

    });

    App.Model.PedidoList = App.Model._PedidoList.extend({
        model: App.Model.PedidoModel
    });

    return  App.Model.PedidoModel;

});