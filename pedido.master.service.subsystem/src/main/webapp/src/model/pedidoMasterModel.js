define(['model/_pedidoMasterModel'], function() { 
    App.Model.PedidoMasterModel = App.Model._PedidoMasterModel.extend({
		initialize: function() {
            this.on('invalid', function(model,error) {
                Backbone.trigger('pedido-master-model-error', error);
            });
        },
        validate: function(attrs, options){
        	var modelMaster = new App.Model.PedidoModel();
        	if(modelMaster.validate){
            	return modelMaster.validate(attrs.pedidoEntity,options);
            }
        }
    });

    App.Model.PedidoMasterList = App.Model._PedidoMasterList.extend({
        model: App.Model.PedidoMasterModel
    });

    return  App.Model.PedidoMasterModel;

});