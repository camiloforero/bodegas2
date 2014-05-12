define(['component/_CRUDComponent', 'controller/toolbarController', 'model/toolbarModel', 'model/itemInventarioModel', 'controller/itemInventarioController'], function() {
    App.Component.ItemInventarioComponent = App.Component._CRUDComponent.extend({
        name: 'itemInventario',
        model: App.Model.ItemInventarioModel,
        listModel: App.Model.ItemInventarioList,
        controller : App.Controller.ItemInventarioController
    });
    return App.Component.ItemInventarioComponent;
});