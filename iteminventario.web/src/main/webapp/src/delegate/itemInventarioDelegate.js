define(['delegate/_itemInventarioDelegate'], function() {
    App.Delegate.ItemInventarioDelegate = App.Delegate._ItemInventarioDelegate.extend({
        
        nombreBodega : function(id,callback,callbackError){
	    console.log('Satisfacer: '+id);
            $.ajax({
	          url: '/bodega.service.subsystem.web/webresources/Bodega/'+id,
	          type: 'GET',
	          datatype: 'application/json',
	          contentType: 'application/json'
	      }).done(_.bind(function(data){
                  callback(data.id, data.name);
	      },this)).error(_.bind(function(data){
	    	  callbackError(data);
	      },this));
	}
        
    });
});