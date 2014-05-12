define(['component/_CRUDComponent', 'controller/toolbarController', 'model/toolbarModel', 'model/ordenModel', 'controller/ordenController'], function() {
    App.Component.OrdenComponent = App.Component._CRUDComponent.extend({
        name: 'orden',
        model: App.Model.OrdenModel,
        listModel: App.Model.OrdenList,
        controller : App.Controller.OrdenController
    });
    return App.Component.OrdenComponent;
});