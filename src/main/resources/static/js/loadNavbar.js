/*

*/

function loadNavbar(page="Dashboard") {
    

const navbarContent = `
<div class="container-fluid py-1 px-3">
    <nav aria-label="breadcrumb">
        <!-- <h5 class="font-weight-bolder pt-3">Dashboard</h5> -->
        <!-- <h5 class="font-weight-bolder pt-3 display-6">Dashboard</h5> -->
        <h5 class="pt-3 display-6 text-primary font-weight-bolder">${page}</h5>
    </nav>
    
    <div class="collapse navbar-collapse mt-sm-0 mt-2 me-md-0 me-sm-4" id="navbar">
        <div class="ms-md-auto pe-md-3 d-flex align-items-center ">
            <div class="input-group d-none">
                <span class="input-group-text text-body"><i class="fas fa-search" aria-hidden="true"></i></span>
                <input type="text" class="form-control" placeholder="Type here...">
            </div>
        </div>

        <ul class="navbar-nav justify-content-end">
            <li class="nav-item d-xl-none ps-3 d-flex align-items-center">
                <a href="javascript:;" class="nav-link text-body p-0" id="iconNavbarSidenav">
                <div class="sidenav-toggler-inner">
                    <i class="sidenav-toggler-line"></i>
                    <i class="sidenav-toggler-line"></i>
                    <i class="sidenav-toggler-line"></i>
                </div>
                </a>
            </li>
        </ul>
    </div>
</div>
`


    const navbar = document.querySelector("#navbarBlur")
    navbar.innerHTML =  navbarContent 
    console.log("Loaded navbar")
}
// ----------------------------------
