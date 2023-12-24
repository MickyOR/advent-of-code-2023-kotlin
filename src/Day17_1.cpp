#include <bits/stdc++.h>

using namespace std;

int main() {
    vector<string> mat;
    string aux;
    while (cin >> aux) {
        mat.push_back(aux);
    }
    int n = mat.size();
    int m = mat[0].size();
    int dr[] = {-1, 0, 1, 0};
    int dc[] = {0, 1, 0, -1};
    // {dist, { {row, col}, {dir, consecutive} }}
    set<pair<int, pair<pair<int, int>, pair<int, int>>>> s;
    vector<vector<vector<vector<int>>>> dist(n, vector<vector<vector<int>>> (m, vector<vector<int>> (4, vector<int> (4, 1e9))));
    s.insert({0, {{0, 0}, {1, 0}}});
    dist[0][0][1][0] = 0;
    while (!s.empty()) {
        auto it = s.begin();
        int curDist = it->first;
        int row = it->second.first.first;
        int col = it->second.first.second;
        int dir = it->second.second.first;
        int consecutive = it->second.second.second;
        s.erase(it);
        if (curDist > dist[row][col][dir][consecutive]) continue;
        for (int k = 0; k < 4; k++) {
            if ((dir+2)%4 == k) continue;
            if (dir == k && consecutive == 3) continue;
            int newRow = row + dr[k];
            int newCol = col + dc[k];
            if (newRow < 0 || newRow >= n || newCol < 0 || newCol >= m) continue;
            int newDist = curDist + mat[newRow][newCol] - '0';
            int newCons = (dir == k) ? consecutive + 1 : 1;
            if (newDist < dist[newRow][newCol][k][newCons]) {
                s.erase({dist[newRow][newCol][k][newCons], {{newRow, newCol}, {k, newCons}}});
                dist[newRow][newCol][k][newCons] = newDist;
                s.insert({newDist, {{newRow, newCol}, {k, newCons}}});
            }
        }
    }
    int ans = 1e9;
    for (int i = 0; i < 4; i++) {
        for (int j = 0; j < 4; j++) {
            ans = min(ans, dist[n-1][m-1][i][j]);
        }
    }
    cout << ans << endl;
    return 0;
}