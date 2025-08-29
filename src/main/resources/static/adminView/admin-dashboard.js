
  function showSection(sectionId) {
    document.querySelectorAll(".card").forEach((card) => {
      card.style.display = "none";
    });
    const section = document.getElementById(sectionId);
    if (section) section.style.display = "block";
  }

  async function fetchAdminDetails() {
    try {
      const response = await fetch("/admin/current");
      if (response.ok) {
        const admin = await response.json();
        document.querySelector("#profile .profile-details").innerHTML = `
          <div class="detail-row"><span class="label">Admin ID</span><span class="value">${admin.adminId}</span></div>
          <div class="detail-row"><span class="label">Name</span><span class="value">${admin.adminName}</span></div>
          <div class="detail-row"><span class="label">Email</span><span class="value">${admin.emailId}</span></div>
          <div class="detail-row"><span class="label">Access</span><span class="value">${admin.access}</span></div>
        `;
      } else {
        window.location.href = "/index.html?session=expired";
      }
    } catch {
      window.location.href = "/index.html?session=expired";
    }
  }

 async function checkAdminAccess() {
   try {
     const res = await fetch("/admin/access-level");
     if (!res.ok) return;

     const access = await res.text();
     const menuList = document.getElementById("menuList");
     const addAdminCard = document.getElementById("addAdmin");

     if (access.includes("FULL_ACCESS")) {
       if (![...menuList.children].some((li) => li.textContent.includes("Add Admin"))) {
         const li = document.createElement("li");
         li.textContent = "Add Admin";
         li.onclick = () => showSection("addAdmin");
         const searchTxLi = [...menuList.children].find((li) =>
           li.textContent.includes("Search Transactions")
         );
         menuList.insertBefore(li, searchTxLi);
       }

       if (addAdminCard) {
         addAdminCard.style.display = "block";

         const adminForm = document.getElementById("adminForm");
         if (adminForm && !adminForm.hasAttribute("data-listener-attached")) {
           adminForm.setAttribute("data-listener-attached", "true");
           adminForm.addEventListener("submit", async (e) => {
             e.preventDefault();
             const msgBox = document.getElementById("adminMessage");
             msgBox.textContent = "";
console.log("adminName:", document.getElementById("adminNameInput"));
console.log("emailId:", document.getElementById("emailId"));
console.log("contactNumber:", document.getElementById("contactNumber"));
console.log("access:", document.getElementById("access"));
             const adminData = {
               adminName: document.getElementById("adminNameInput").value.trim(),
               emailId: document.getElementById("emailId").value.trim(),
               contactNumber: document.getElementById("contactNumber").value.trim(),
               access: document.getElementById("access").value.trim(),
             };

             try {
               const response = await fetch("/admin/new-admin-creation", {
                 method: "POST",
                 headers: { "Content-Type": "application/json" },
                 body: JSON.stringify(adminData),
               });

               if (response.ok) {
                 msgBox.style.color = "green";
                 msgBox.textContent = "Admin Added Successfully!";
                 adminForm.reset();
               } else {
                 msgBox.style.color = "red";
                 msgBox.textContent = "Failed to add admin, check inputs!";
               }
             } catch (err) {
               msgBox.style.color = "red";
               msgBox.textContent = "Request failed: " + err;
             }
           });
         }
       }

     } else {
       const addAdminLi = [...menuList.children].find((li) =>
         li.textContent.includes("Add Admin")
       );
       if (addAdminLi) addAdminLi.remove();
       if (addAdminCard) addAdminCard.style.display = "none";
     }
   } catch (err) {
     console.error("Access check failed:", err);
   }
 }

  async function showCustomers() {
    showSection("listOfCustomers");
    const tableBody = document.getElementById("customerTableBody");
    tableBody.innerHTML = "";

    try {
      const response = await fetch("/admin/list-users");
      if (!response.ok) throw new Error("Failed to fetch customers");

      const customers = await response.json();
      if (customers.length === 0) {
        tableBody.innerHTML = `<tr><td colspan="7" style="text-align:center; color:gray;">No customers found</td></tr>`;
        return;
      }

      customers.forEach((cust) => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${cust.id}</td>
          <td>${cust.accNumber}</td>
          <td>${cust.accHolderName}</td>
          <td>${cust.accHolderLocation}</td>
          <td>${cust.emailId}</td>
          <td>${cust.contactNumber}</td>
          <td>₹${cust.accBalance.toFixed(2)}</td>
        `;
        tableBody.appendChild(row);
      });
    } catch {
      tableBody.innerHTML = `<tr><td colspan="7" style="text-align:center; color:red;">Failed to load customers</td></tr>`;
    }
  }

  document.querySelector("#transactionSearchForm")
    ?.addEventListener("submit", async function (e) {
      e.preventDefault();
      const accNum = document.getElementById("transactionAccountNumber").value;
      const tbody = document.querySelector("#searchTransactions tbody");
      tbody.innerHTML = "";

      try {
        const response = await fetch(`/admin/transactions-by-accNum?accNumber=${accNum}`);
        if (!response.ok) throw new Error("No transactions found");

        const transactions = await response.json();
        if (transactions.length === 0) {
          tbody.innerHTML = '<tr><td colspan="7">No transactions found</td></tr>';
          return;
        }

        transactions.forEach((tx, index) => {
          const row = `
            <tr>
              <td>${index + 1}</td>
              <td>${new Date(tx.timestamp).toLocaleString()}</td>
              <td>${tx.senderAccNum || "-"}</td>
              <td>${tx.receiverAccNum || "-"}</td>
              <td>${tx.amount}</td>
              <td>${tx.accountBalance}</td>
              <td>${tx.message}</td>
            </tr>
          `;
          tbody.innerHTML += row;
        });
      } catch (err) {
        tbody.innerHTML = '<tr><td colspan="7">User not found</td></tr>';
      }
    });

  document.getElementById("adminForm")
    ?.addEventListener("submit", async function (e) {
      e.preventDefault();
      const msgBox = document.getElementById("adminMessage");
      msgBox.textContent = "";

      const adminData = {
        adminName: document.getElementById("adminName").value.trim(),
        emailId: document.getElementById("emailId").value.trim(),
        contactNumber: document.getElementById("contactNumber").value.trim(),
        access: document.getElementById("access").value.trim(),
      };

      try {
        const response = await fetch("/admin/new-admin-creation", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify(adminData),
        });

        if (response.ok) {
          msgBox.style.color = "green";
          msgBox.textContent = "Admin Added Successfully!";
          document.getElementById("adminForm").reset();
        } else {
          msgBox.style.color = "red";
          msgBox.textContent = "Failed to add admin, Please check the inputs!";
        }
      } catch (err) {
        msgBox.style.color = "red";
        msgBox.textContent = "Request failed: " + err;
      }
    });

  function redirectToSearchUser(event) {
    event.preventDefault();
    const accNumber = document.getElementById("accNumber").value.trim();
    if (accNumber) {
      window.location.href = `searchuser.html?accNumber=${encodeURIComponent(accNumber)}`;
    }
  }
document.addEventListener("DOMContentLoaded", async () => {
  await fetchAdminDetails();
  await checkAdminAccess();
  showSection("profile");
const form = document.getElementById("searchUserForm");
  const msgBox = document.getElementById("searchUserMessage");

  if (form) {
    form.addEventListener("submit", async (e) => {
      e.preventDefault();
      msgBox.textContent = "";

      const accNumber = document.getElementById("accNumber").value.trim();
      console.log(accNumber);
      if (!accNumber) return;

      try {
        const res = await fetch(`/admin/user-by-accNum?accNumber=${accNumber}`);
        if (res.ok) {
          const user = await res.json();

          if (user) {
             window.location.href = `searchuser.html?accNumber=${accNumber}`;
          } else {
            msgBox.style.color = "red";
            msgBox.textContent =
              "No user found with this account number!";
          }
        } else {
          msgBox.style.color = "red";
          msgBox.textContent = "Failed to search. Try again.";
        }
      } catch (err) {
        msgBox.style.color = "red";
        msgBox.textContent = "Request failed: " + err;
      }
    });
  }
});
