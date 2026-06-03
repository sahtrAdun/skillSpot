package dot.adun.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BackHand
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dot.adun.core.domain.entity.UserRole
import dot.adun.core.ui.theme.AppTheme

@Composable
fun UserRoleResolver(
    forFreelancers: @Composable (Modifier) -> Unit,
    forCustomers: @Composable (Modifier) -> Unit,
    modifier: Modifier = Modifier
) {
    val userRole = LocalUserRole.current
    when (userRole) {
        UserRole.Freelancer -> forFreelancers(modifier)
        UserRole.Customer -> forCustomers(modifier)
        else -> NoneRole()
    }
}

@Composable
private fun NoneRole() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.BackHand,
            contentDescription = null,
            tint = AppTheme.colors.text.secondary,
            modifier = Modifier.requiredSize(64.dp)
        )

        Text(
            text = "No role found",
            color = AppTheme.colors.text.secondary,
            style = AppTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}
