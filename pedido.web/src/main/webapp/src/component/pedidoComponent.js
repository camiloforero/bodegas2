define(['component/_CRUDComponent', 'controller/toolbarController', 'model/toolbarModel', 'model/pedidoModel', 'controller/pedidoController'], function() {
    App.Component.PedidoComponent = App.Component._CRUDComponent.extend({
        name: 'pedido',
        model: App.Model.PedidoModel,
        listModel: App.Model.PedidoList,
        controller : App.Controller.PedidoController
    });
    return App.Component.PedidoComponent;
});