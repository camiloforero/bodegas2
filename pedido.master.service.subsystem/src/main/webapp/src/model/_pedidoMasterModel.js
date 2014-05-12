define([], function() {
    App.Model._PedidoMasterModel = Backbone.Model.extend({
     
    });

    App.Model._PedidoMasterList = Backbone.Collection.extend({
        model: App.Model._PedidoMasterModel,
        initialize: function() {
        }

    });
    return App.Model._PedidoMasterModel;
    
});