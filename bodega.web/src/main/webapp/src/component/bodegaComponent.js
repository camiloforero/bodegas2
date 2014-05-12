define(['component/_CRUDComponent', 'controller/toolbarController', 'model/toolbarModel', 'model/bodegaModel', 'controller/bodegaController'], function() {
    App.Component.BodegaComponent = App.Component._CRUDComponent.extend({
        name: 'bodega',
        model: App.Model.BodegaModel,
        listModel: App.Model.BodegaList,
        controller : App.Controller.BodegaController
    });
    return App.Component.BodegaComponent;
});