
const script = document.createElement("script");
script.src = "https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js";
document.head.appendChild(script);
document.addEventListener("DOMContentLoaded", function () {
  var myTimeOut;
  var alertElement = document.getElementById('alertt');
  var closeButton = document.querySelector('.close');

  function mytimeoutfunction() {
    if (alertElement) {
      alertElement.style.transition = "opacity 3s";
      alertElement.style.opacity = 0;
    }
  }

  // Set initial timeout
  myTimeOut = setTimeout(mytimeoutfunction, 600);

  if (alertElement) {
    alertElement.addEventListener('mouseout', function () {
      myTimeOut = setTimeout(mytimeoutfunction, 600);
    });

    alertElement.addEventListener('mouseover', function () {
      clearTimeout(myTimeOut);
    });
  }

  if (closeButton) {
    closeButton.addEventListener('click', mytimeoutfunction);
  }
});
/*setTimeout(function() {
  $('#alertt').fadeOut('fast');
}, 3000);*/


$('#description').keydown( function(e) {
  var key = event.keyCode || event.charCode;

    if(key == 8)
       {
		return true
		}
    else if( $(this).val().length <= 70 )
     { 
     return true;
      }
    else { 
		alert('Description should be less than 70 words..!');
		return false
		}
		

});
	var mainNav = document.getElementById('mainNav')
      var heroActive = false // To enhance performance, I chose this global variable to track the navbar changes.
      window.addEventListener('scroll', function() {
        if (window.scrollY > 20) {
          if (!heroActive) {
            heroActive = true
            mainNav.classList.add('bg-dark')
            
          }
        } else {
          if (heroActive) {
            heroActive = false
            mainNav.classList.remove('bg-dark')
              }
        }
      });
      
       window.onload = function() {
    document.getElementById("check").checked = true;
    }
 
 
 $('#imgUpload').click(function(){
   $("input[type='file']").trigger('click');
})

$("input[type='file']").change(function(){
   $('#val').text(this.value.replace(/C:\\fakepath\\/i, ''))
})    



function imageUpload(){

	$(document).find("#send_photo1").click();
	$(document).find("#send_photo1").on("change", function(){
		$(document).find("#upload").click();
	})
}


