document.addEventListener("DOMContentLoaded", function () {
    // Alert Timeout Logic
    const alertElement = document.getElementById('alertt');
    const closeButton = document.querySelector('.close');
    let myTimeOut;

    const mytimeoutfunction = () => {
        if (alertElement) {
            alertElement.style.transition = "opacity 3s";
            alertElement.style.opacity = 0;
        }
    };

    // Set initial timeout for alert
    if (alertElement) {
        myTimeOut = setTimeout(mytimeoutfunction, 600);

        alertElement.addEventListener('mouseout', () => {
            myTimeOut = setTimeout(mytimeoutfunction, 600);
        });

        alertElement.addEventListener('mouseover', () => {
            clearTimeout(myTimeOut);
        });
    }

    if (closeButton) {
        closeButton.addEventListener('click', mytimeoutfunction);
    }

    // Auto Close Alert After 5 Seconds
    if (alertElement) {
        setTimeout(() => {
            alertElement.classList.add('hide');
            setTimeout(() => {
                alertElement.remove();
            }, 500);
        }, 5000);
    }

    // Description Length Check
    $('#description').keydown(function (e) {
        const key = e.keyCode || e.charCode;
        if (key === 8 || $(this).val().length <= 70) {
            return true;
        } else {
            alert('Description should be less than 70 words..!');
            return false;
        }
    });

    // Navbar Scroll Behavior
    const mainNav = document.getElementById('mainNav');
    let heroActive = false;
    window.addEventListener('scroll', function () {
        if (window.scrollY > 20) {
            if (!heroActive) {
                heroActive = true;
                mainNav.classList.add('bg-dark');
            }
        } else if (heroActive) {
            heroActive = false;
            mainNav.classList.remove('bg-dark');
        }
    });

    // Checkbox Default Checked on Page Load
    const checkElement = document.getElementById("check");
    if (checkElement) {
        checkElement.checked = true;
    }

    // Image Upload Click and Change Behavior
    $('#imgUpload').click(() => {
        $("input[type='file']").trigger('click');
    });

    $("input[type='file']").change(function () {
        $('#val').text(this.value.replace(/C:\\fakepath\\/i, ''));
    });

    const imageUpload = () => {
        const sendPhoto = $("#send_photo1");
        if (sendPhoto.length) {
            sendPhoto.click();
            sendPhoto.on("change", function () {
                $("#upload").click();
            });
        }
    };

    // Mobile Menu Toggle
    const navbarToggler = document.querySelector(".navbar-toggler");
    const mobileMenu = document.getElementById("mobileMenu");

    if (navbarToggler && mobileMenu) {
        navbarToggler.addEventListener("click", function () {
            mobileMenu.classList.toggle("active");
        });

        // Close mobile menu when a link is clicked
        mobileMenu.querySelectorAll("a").forEach(link => {
            link.addEventListener("click", () => {
                mobileMenu.classList.remove("active");
            });
        });

        // Close mobile menu when clicking outside
        document.addEventListener("click", function (event) {
            if (!navbarToggler.contains(event.target) && !mobileMenu.contains(event.target)) {
                mobileMenu.classList.remove("active");
            }
        });
    }
});
$(document).ready(function () {
    const navbarToggler = $(".navbar-toggler");
    const mobileMenu = $("#mobileMenu");

    // Toggle mobile menu on button click
    navbarToggler.on("click", function (event) {
        event.preventDefault();
        mobileMenu.toggleClass("active");
    });

    // Close mobile menu when a link is clicked
    mobileMenu.find("a").on("click", function () {
        mobileMenu.removeClass("active");
    });

    // Close mobile menu when clicking outside
    $(document).on("click", function (event) {
        if (!navbarToggler.is(event.target) && !navbarToggler.has(event.target).length &&
            !mobileMenu.is(event.target) && !mobileMenu.has(event.target).length) {
            mobileMenu.removeClass("active");
        }
    });
});
