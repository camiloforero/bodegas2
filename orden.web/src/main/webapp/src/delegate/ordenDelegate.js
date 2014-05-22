define(['delegate/_ordenDelegate'], function() {
    App.Delegate.OrdenDelegate = App.Delegate._OrdenDelegate.extend(
    
            {
        
        satisfacer : function(id,callback,callbackError){
	    console.log('Satisfacer: '+id);
            $.ajax({
	          url: '/orden.service.subsystem/webresources/Orden/'+id+'/satisfacer',
	          type: 'GET',
	          datatype: 'application/json',
	          contentType: 'application/json'
	      }).done(_.bind(function(data){
	    	  callback(data);
	      },this)).error(_.bind(function(data){
	    	  callbackError(data);
	      },this));
	}
                
    }
            
            );




});