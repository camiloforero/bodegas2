define(['model/_bodegaMasterModel'], function() { 
    App.Model.BodegaMasterModel = App.Model._BodegaMasterModel.extend({
		initialize: function() {
            this.on('invalid', function(model,error) {
                Backbone.trigger('bodega-master-model-error', error);
            });
        },
        validate: function(attrs, options){
        	var modelMaster = new App.Model.BodegaModel();
        	if(modelMaster.validate){
            	return modelMaster.validate(attrs.bodegaEntity,options);
            }
        }
    });

    App.Model.BodegaMasterList = App.Model._BodegaMasterList.extend({
        model: App.Model.BodegaMasterModel
    });

    return  App.Model.BodegaMasterModel;

});