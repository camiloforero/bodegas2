define(['component/_CRUDComponent', 'controller/toolbarController', 'model/toolbarModel', 'model/logModel', 'controller/logController'], function() {
    App.Component.LogComponent = App.Component._CRUDComponent.extend({
        name: 'log',
        model: App.Model.LogModel,
        listModel: App.Model.LogList,
        controller : App.Controller.LogController
    });
    return App.Component.LogComponent;
});